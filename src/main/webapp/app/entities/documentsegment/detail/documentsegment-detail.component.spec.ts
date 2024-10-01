import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DocumentsegmentDetailComponent } from './documentsegment-detail.component';

describe('Documentsegment Management Detail Component', () => {
  let comp: DocumentsegmentDetailComponent;
  let fixture: ComponentFixture<DocumentsegmentDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentsegmentDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./documentsegment-detail.component').then(m => m.DocumentsegmentDetailComponent),
              resolve: { documentsegment: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DocumentsegmentDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentsegmentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentsegment on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DocumentsegmentDetailComponent);

      // THEN
      expect(instance.documentsegment()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
