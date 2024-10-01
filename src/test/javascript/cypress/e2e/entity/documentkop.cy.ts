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

describe('Documentkop e2e test', () => {
  const documentkopPageUrl = '/documentkop';
  const documentkopPageUrlPattern = new RegExp('/documentkop(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const documentkopSample = {};

  let documentkop;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/documentkops+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/documentkops').as('postEntityRequest');
    cy.intercept('DELETE', '/api/documentkops/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (documentkop) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/documentkops/${documentkop.id}`,
      }).then(() => {
        documentkop = undefined;
      });
    }
  });

  it('Documentkops menu should load Documentkops page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('documentkop');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Documentkop').should('exist');
    cy.url().should('match', documentkopPageUrlPattern);
  });

  describe('Documentkop page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(documentkopPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Documentkop page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/documentkop/new$'));
        cy.getEntityCreateUpdateHeading('Documentkop');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentkopPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/documentkops',
          body: documentkopSample,
        }).then(({ body }) => {
          documentkop = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/documentkops+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/documentkops?page=0&size=20>; rel="last",<http://localhost/api/documentkops?page=0&size=20>; rel="first"',
              },
              body: [documentkop],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(documentkopPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Documentkop page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('documentkop');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentkopPageUrlPattern);
      });

      it('edit button click should load edit Documentkop page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Documentkop');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentkopPageUrlPattern);
      });

      it('edit button click should load edit Documentkop page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Documentkop');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentkopPageUrlPattern);
      });

      it('last delete button click should delete instance of Documentkop', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('documentkop').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', documentkopPageUrlPattern);

        documentkop = undefined;
      });
    });
  });

  describe('new Documentkop page', () => {
    beforeEach(() => {
      cy.visit(`${documentkopPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Documentkop');
    });

    it('should create an instance of Documentkop', () => {
      cy.get(`[data-cy="bedrijfsnummer"]`).type('poli');
      cy.get(`[data-cy="bedrijfsnummer"]`).should('have.value', 'poli');

      cy.get(`[data-cy="documentNrBoekhoudingsdocument"]`).type('toady yaho');
      cy.get(`[data-cy="documentNrBoekhoudingsdocument"]`).should('have.value', 'toady yaho');

      cy.get(`[data-cy="boekjaar"]`).type('yaho');
      cy.get(`[data-cy="boekjaar"]`).should('have.value', 'yaho');

      cy.get(`[data-cy="documentsoort"]`).select('INKOMENDE_FACTUUR');

      cy.get(`[data-cy="documentdatum"]`).type('2024-09-30T21:39');
      cy.get(`[data-cy="documentdatum"]`).blur();
      cy.get(`[data-cy="documentdatum"]`).should('have.value', '2024-09-30T21:39');

      cy.get(`[data-cy="boekingsdatum"]`).type('2024-09-30T22:02');
      cy.get(`[data-cy="boekingsdatum"]`).blur();
      cy.get(`[data-cy="boekingsdatum"]`).should('have.value', '2024-09-30T22:02');

      cy.get(`[data-cy="boekmaand"]`).select('FE');

      cy.get(`[data-cy="invoerdag"]`).type('2024-10-01T02:39');
      cy.get(`[data-cy="invoerdag"]`).blur();
      cy.get(`[data-cy="invoerdag"]`).should('have.value', '2024-10-01T02:39');

      cy.get(`[data-cy="invoertijd"]`).type('2024-10-01T11:37');
      cy.get(`[data-cy="invoertijd"]`).blur();
      cy.get(`[data-cy="invoertijd"]`).should('have.value', '2024-10-01T11:37');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        documentkop = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', documentkopPageUrlPattern);
    });
  });
});
