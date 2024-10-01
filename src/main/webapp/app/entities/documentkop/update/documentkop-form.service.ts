import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocumentkop, NewDocumentkop } from '../documentkop.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentkop for edit and NewDocumentkopFormGroupInput for create.
 */
type DocumentkopFormGroupInput = IDocumentkop | PartialWithRequiredKeyOf<NewDocumentkop>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocumentkop | NewDocumentkop> = Omit<T, 'documentdatum' | 'boekingsdatum' | 'invoerdag' | 'invoertijd'> & {
  documentdatum?: string | null;
  boekingsdatum?: string | null;
  invoerdag?: string | null;
  invoertijd?: string | null;
};

type DocumentkopFormRawValue = FormValueOf<IDocumentkop>;

type NewDocumentkopFormRawValue = FormValueOf<NewDocumentkop>;

type DocumentkopFormDefaults = Pick<NewDocumentkop, 'id' | 'documentdatum' | 'boekingsdatum' | 'invoerdag' | 'invoertijd'>;

type DocumentkopFormGroupContent = {
  id: FormControl<DocumentkopFormRawValue['id'] | NewDocumentkop['id']>;
  bedrijfsnummer: FormControl<DocumentkopFormRawValue['bedrijfsnummer']>;
  documentNrBoekhoudingsdocument: FormControl<DocumentkopFormRawValue['documentNrBoekhoudingsdocument']>;
  boekjaar: FormControl<DocumentkopFormRawValue['boekjaar']>;
  documentsoort: FormControl<DocumentkopFormRawValue['documentsoort']>;
  documentdatum: FormControl<DocumentkopFormRawValue['documentdatum']>;
  boekingsdatum: FormControl<DocumentkopFormRawValue['boekingsdatum']>;
  boekmaand: FormControl<DocumentkopFormRawValue['boekmaand']>;
  invoerdag: FormControl<DocumentkopFormRawValue['invoerdag']>;
  invoertijd: FormControl<DocumentkopFormRawValue['invoertijd']>;
};

export type DocumentkopFormGroup = FormGroup<DocumentkopFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentkopFormService {
  createDocumentkopFormGroup(documentkop: DocumentkopFormGroupInput = { id: null }): DocumentkopFormGroup {
    const documentkopRawValue = this.convertDocumentkopToDocumentkopRawValue({
      ...this.getFormDefaults(),
      ...documentkop,
    });
    return new FormGroup<DocumentkopFormGroupContent>({
      id: new FormControl(
        { value: documentkopRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      bedrijfsnummer: new FormControl(documentkopRawValue.bedrijfsnummer, {
        validators: [Validators.maxLength(4)],
      }),
      documentNrBoekhoudingsdocument: new FormControl(documentkopRawValue.documentNrBoekhoudingsdocument, {
        validators: [Validators.maxLength(10)],
      }),
      boekjaar: new FormControl(documentkopRawValue.boekjaar, {
        validators: [Validators.maxLength(4)],
      }),
      documentsoort: new FormControl(documentkopRawValue.documentsoort),
      documentdatum: new FormControl(documentkopRawValue.documentdatum),
      boekingsdatum: new FormControl(documentkopRawValue.boekingsdatum),
      boekmaand: new FormControl(documentkopRawValue.boekmaand),
      invoerdag: new FormControl(documentkopRawValue.invoerdag),
      invoertijd: new FormControl(documentkopRawValue.invoertijd),
    });
  }

  getDocumentkop(form: DocumentkopFormGroup): IDocumentkop | NewDocumentkop {
    return this.convertDocumentkopRawValueToDocumentkop(form.getRawValue() as DocumentkopFormRawValue | NewDocumentkopFormRawValue);
  }

  resetForm(form: DocumentkopFormGroup, documentkop: DocumentkopFormGroupInput): void {
    const documentkopRawValue = this.convertDocumentkopToDocumentkopRawValue({ ...this.getFormDefaults(), ...documentkop });
    form.reset(
      {
        ...documentkopRawValue,
        id: { value: documentkopRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DocumentkopFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      documentdatum: currentTime,
      boekingsdatum: currentTime,
      invoerdag: currentTime,
      invoertijd: currentTime,
    };
  }

  private convertDocumentkopRawValueToDocumentkop(
    rawDocumentkop: DocumentkopFormRawValue | NewDocumentkopFormRawValue,
  ): IDocumentkop | NewDocumentkop {
    return {
      ...rawDocumentkop,
      documentdatum: dayjs(rawDocumentkop.documentdatum, DATE_TIME_FORMAT),
      boekingsdatum: dayjs(rawDocumentkop.boekingsdatum, DATE_TIME_FORMAT),
      invoerdag: dayjs(rawDocumentkop.invoerdag, DATE_TIME_FORMAT),
      invoertijd: dayjs(rawDocumentkop.invoertijd, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentkopToDocumentkopRawValue(
    documentkop: IDocumentkop | (Partial<NewDocumentkop> & DocumentkopFormDefaults),
  ): DocumentkopFormRawValue | PartialWithRequiredKeyOf<NewDocumentkopFormRawValue> {
    return {
      ...documentkop,
      documentdatum: documentkop.documentdatum ? documentkop.documentdatum.format(DATE_TIME_FORMAT) : undefined,
      boekingsdatum: documentkop.boekingsdatum ? documentkop.boekingsdatum.format(DATE_TIME_FORMAT) : undefined,
      invoerdag: documentkop.invoerdag ? documentkop.invoerdag.format(DATE_TIME_FORMAT) : undefined,
      invoertijd: documentkop.invoertijd ? documentkop.invoertijd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
