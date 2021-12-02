import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Manufacturer e2e test', () => {
  const manufacturerPageUrl = '/manufacturer';
  const manufacturerPageUrlPattern = new RegExp('/manufacturer(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const manufacturerSample = { name: 'Seychelles Loan' };

  let manufacturer: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/manufacturers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/manufacturers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/manufacturers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (manufacturer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/manufacturers/${manufacturer.id}`,
      }).then(() => {
        manufacturer = undefined;
      });
    }
  });

  it('Manufacturers menu should load Manufacturers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('manufacturer');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Manufacturer').should('exist');
    cy.url().should('match', manufacturerPageUrlPattern);
  });

  describe('Manufacturer page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(manufacturerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Manufacturer page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/manufacturer/new$'));
        cy.getEntityCreateUpdateHeading('Manufacturer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', manufacturerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/manufacturers',
          body: manufacturerSample,
        }).then(({ body }) => {
          manufacturer = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/manufacturers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [manufacturer],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(manufacturerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Manufacturer page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('manufacturer');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', manufacturerPageUrlPattern);
      });

      it('edit button click should load edit Manufacturer page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Manufacturer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', manufacturerPageUrlPattern);
      });

      it('last delete button click should delete instance of Manufacturer', () => {
        cy.intercept('GET', '/api/manufacturers/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('manufacturer').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', manufacturerPageUrlPattern);

        manufacturer = undefined;
      });
    });
  });

  describe('new Manufacturer page', () => {
    beforeEach(() => {
      cy.visit(`${manufacturerPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Manufacturer');
    });

    it('should create an instance of Manufacturer', () => {
      cy.get(`[data-cy="name"]`).type('Web engage').should('have.value', 'Web engage');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        manufacturer = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', manufacturerPageUrlPattern);
    });
  });
});
