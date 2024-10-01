import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Documentsegment e2e test', () => {
  const documentsegmentPageUrl = '/documentsegment';
  const documentsegmentPageUrlPattern = new RegExp('/documentsegment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const documentsegmentSample = { boekingssleutel: 'ov' };

  let documentsegment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/documentsegments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/documentsegments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/documentsegments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (documentsegment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/documentsegments/${documentsegment.id}`,
      }).then(() => {
        documentsegment = undefined;
      });
    }
  });

  it('Documentsegments menu should load Documentsegments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('documentsegment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Documentsegment').should('exist');
    cy.url().should('match', documentsegmentPageUrlPattern);
  });

  describe('Documentsegment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(documentsegmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Documentsegment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/documentsegment/new$'));
        cy.getEntityCreateUpdateHeading('Documentsegment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentsegmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/documentsegments',
          body: documentsegmentSample,
        }).then(({ body }) => {
          documentsegment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/documentsegments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/documentsegments?page=0&size=20>; rel="last",<http://localhost/api/documentsegments?page=0&size=20>; rel="first"',
              },
              body: [documentsegment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(documentsegmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Documentsegment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('documentsegment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentsegmentPageUrlPattern);
      });

      it('edit button click should load edit Documentsegment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Documentsegment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentsegmentPageUrlPattern);
      });

      it('edit button click should load edit Documentsegment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Documentsegment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentsegmentPageUrlPattern);
      });

      it('last delete button click should delete instance of Documentsegment', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('documentsegment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentsegmentPageUrlPattern);

        documentsegment = undefined;
      });
    });
  });

  describe('new Documentsegment page', () => {
    beforeEach(() => {
      cy.visit(`${documentsegmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Documentsegment');
    });

    it('should create an instance of Documentsegment', () => {
      cy.get(`[data-cy="bedrijfsnummer"]`).type('aha');
      cy.get(`[data-cy="bedrijfsnummer"]`).should('have.value', 'aha');

      cy.get(`[data-cy="documentNrBoekhoudingsdocument"]`).type('against ti');
      cy.get(`[data-cy="documentNrBoekhoudingsdocument"]`).should('have.value', 'against ti');

      cy.get(`[data-cy="boekjaar"]`).type('thro');
      cy.get(`[data-cy="boekjaar"]`).should('have.value', 'thro');

      cy.get(`[data-cy="boekingsregelNrBoekhoudingsdocument"]`).type('unt');
      cy.get(`[data-cy="boekingsregelNrBoekhoudingsdocument"]`).should('have.value', 'unt');

      cy.get(`[data-cy="boekingsregelIdentificatie"]`).type('r');
      cy.get(`[data-cy="boekingsregelIdentificatie"]`).should('have.value', 'r');

      cy.get(`[data-cy="vereffeningsdatum"]`).type('2024-10-01T06:53');
      cy.get(`[data-cy="vereffeningsdatum"]`).blur();
      cy.get(`[data-cy="vereffeningsdatum"]`).should('have.value', '2024-10-01T06:53');

      cy.get(`[data-cy="invoerdatumVereffening"]`).type('2024-09-30T13:18');
      cy.get(`[data-cy="invoerdatumVereffening"]`).blur();
      cy.get(`[data-cy="invoerdatumVereffening"]`).should('have.value', '2024-09-30T13:18');

      cy.get(`[data-cy="vereffeningsdocumentNr"]`).type('citizen ge');
      cy.get(`[data-cy="vereffeningsdocumentNr"]`).should('have.value', 'citizen ge');

      cy.get(`[data-cy="boekingssleutel"]`).type('th');
      cy.get(`[data-cy="boekingssleutel"]`).should('have.value', 'th');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        documentsegment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', documentsegmentPageUrlPattern);
    });
  });
});
