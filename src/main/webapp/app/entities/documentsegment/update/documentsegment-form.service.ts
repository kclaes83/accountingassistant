import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocumentsegment, NewDocumentsegment } from '../documentsegment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentsegment for edit and NewDocumentsegmentFormGroupInput for create.
 */
type DocumentsegmentFormGroupInput = IDocumentsegment | PartialWithRequiredKeyOf<NewDocumentsegment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocumentsegment | NewDocumentsegment> = Omit<T, 'vereffeningsdatum' | 'invoerdatumVereffening'> & {
  vereffeningsdatum?: string | null;
  invoerdatumVereffening?: string | null;
};

type DocumentsegmentFormRawValue = FormValueOf<IDocumentsegment>;

type NewDocumentsegmentFormRawValue = FormValueOf<NewDocumentsegment>;

type DocumentsegmentFormDefaults = Pick<NewDocumentsegment, 'id' | 'vereffeningsdatum' | 'invoerdatumVereffening'>;

type DocumentsegmentFormGroupContent = {
  id: FormControl<DocumentsegmentFormRawValue['id'] | NewDocumentsegment['id']>;
  bedrijfsnummer: FormControl<DocumentsegmentFormRawValue['bedrijfsnummer']>;
  documentNrBoekhoudingsdocument: FormControl<DocumentsegmentFormRawValue['documentNrBoekhoudingsdocument']>;
  boekjaar: FormControl<DocumentsegmentFormRawValue['boekjaar']>;
  boekingsregelNrBoekhoudingsdocument: FormControl<DocumentsegmentFormRawValue['boekingsregelNrBoekhoudingsdocument']>;
  boekingsregelIdentificatie: FormControl<DocumentsegmentFormRawValue['boekingsregelIdentificatie']>;
  vereffeningsdatum: FormControl<DocumentsegmentFormRawValue['vereffeningsdatum']>;
  invoerdatumVereffening: FormControl<DocumentsegmentFormRawValue['invoerdatumVereffening']>;
  vereffeningsdocumentNr: FormControl<DocumentsegmentFormRawValue['vereffeningsdocumentNr']>;
  boekingssleutel: FormControl<DocumentsegmentFormRawValue['boekingssleutel']>;
  documentkop: FormControl<DocumentsegmentFormRawValue['documentkop']>;
};

export type DocumentsegmentFormGroup = FormGroup<DocumentsegmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentsegmentFormService {
  createDocumentsegmentFormGroup(documentsegment: DocumentsegmentFormGroupInput = { id: null }): DocumentsegmentFormGroup {
    const documentsegmentRawValue = this.convertDocumentsegmentToDocumentsegmentRawValue({
      ...this.getFormDefaults(),
      ...documentsegment,
    });
    return new FormGroup<DocumentsegmentFormGroupContent>({
      id: new FormControl(
        { value: documentsegmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      bedrijfsnummer: new FormControl(documentsegmentRawValue.bedrijfsnummer, {
        validators: [Validators.maxLength(4)],
      }),
      documentNrBoekhoudingsdocument: new FormControl(documentsegmentRawValue.documentNrBoekhoudingsdocument, {
        validators: [Validators.maxLength(10)],
      }),
      boekjaar: new FormControl(documentsegmentRawValue.boekjaar, {
        validators: [Validators.maxLength(4)],
      }),
      boekingsregelNrBoekhoudingsdocument: new FormControl(documentsegmentRawValue.boekingsregelNrBoekhoudingsdocument, {
        validators: [Validators.maxLength(3)],
      }),
      boekingsregelIdentificatie: new FormControl(documentsegmentRawValue.boekingsregelIdentificatie, {
        validators: [Validators.maxLength(1)],
      }),
      vereffeningsdatum: new FormControl(documentsegmentRawValue.vereffeningsdatum),
      invoerdatumVereffening: new FormControl(documentsegmentRawValue.invoerdatumVereffening),
      vereffeningsdocumentNr: new FormControl(documentsegmentRawValue.vereffeningsdocumentNr, {
        validators: [Validators.maxLength(10)],
      }),
      boekingssleutel: new FormControl(documentsegmentRawValue.boekingssleutel, {
        validators: [Validators.required, Validators.maxLength(2)],
      }),
      documentkop: new FormControl(documentsegmentRawValue.documentkop),
    });
  }

  getDocumentsegment(form: DocumentsegmentFormGroup): IDocumentsegment | NewDocumentsegment {
    return this.convertDocumentsegmentRawValueToDocumentsegment(
      form.getRawValue() as DocumentsegmentFormRawValue | NewDocumentsegmentFormRawValue,
    );
  }

  resetForm(form: DocumentsegmentFormGroup, documentsegment: DocumentsegmentFormGroupInput): void {
    const documentsegmentRawValue = this.convertDocumentsegmentToDocumentsegmentRawValue({ ...this.getFormDefaults(), ...documentsegment });
    form.reset(
      {
        ...documentsegmentRawValue,
        id: { value: documentsegmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DocumentsegmentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      vereffeningsdatum: currentTime,
      invoerdatumVereffening: currentTime,
    };
  }

  private convertDocumentsegmentRawValueToDocumentsegment(
    rawDocumentsegment: DocumentsegmentFormRawValue | NewDocumentsegmentFormRawValue,
  ): IDocumentsegment | NewDocumentsegment {
    return {
      ...rawDocumentsegment,
      vereffeningsdatum: dayjs(rawDocumentsegment.vereffeningsdatum, DATE_TIME_FORMAT),
      invoerdatumVereffening: dayjs(rawDocumentsegment.invoerdatumVereffening, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentsegmentToDocumentsegmentRawValue(
    documentsegment: IDocumentsegment | (Partial<NewDocumentsegment> & DocumentsegmentFormDefaults),
  ): DocumentsegmentFormRawValue | PartialWithRequiredKeyOf<NewDocumentsegmentFormRawValue> {
    return {
      ...documentsegment,
      vereffeningsdatum: documentsegment.vereffeningsdatum ? documentsegment.vereffeningsdatum.format(DATE_TIME_FORMAT) : undefined,
      invoerdatumVereffening: documentsegment.invoerdatumVereffening
        ? documentsegment.invoerdatumVereffening.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
