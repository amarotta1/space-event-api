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

describe('SpaceShip e2e test', () => {
  const spaceShipPageUrl = '/space-ship';
  const spaceShipPageUrlPattern = new RegExp('/space-ship(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const spaceShipSample = { name: 'Glorieta Marroquinería Corea', type: 'PLASMA' };

  let spaceShip: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/space-ships+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/space-ships').as('postEntityRequest');
    cy.intercept('DELETE', '/api/space-ships/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (spaceShip) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/space-ships/${spaceShip.id}`,
      }).then(() => {
        spaceShip = undefined;
      });
    }
  });

  it('SpaceShips menu should load SpaceShips page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('space-ship');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SpaceShip').should('exist');
    cy.url().should('match', spaceShipPageUrlPattern);
  });

  describe('SpaceShip page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(spaceShipPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SpaceShip page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/space-ship/new$'));
        cy.getEntityCreateUpdateHeading('SpaceShip');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceShipPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/space-ships',
          body: spaceShipSample,
        }).then(({ body }) => {
          spaceShip = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/space-ships+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [spaceShip],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(spaceShipPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SpaceShip page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('spaceShip');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceShipPageUrlPattern);
      });

      it('edit button click should load edit SpaceShip page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SpaceShip');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceShipPageUrlPattern);
      });

      it('last delete button click should delete instance of SpaceShip', () => {
        cy.intercept('GET', '/api/space-ships/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('spaceShip').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceShipPageUrlPattern);

        spaceShip = undefined;
      });
    });
  });

  describe('new SpaceShip page', () => {
    beforeEach(() => {
      cy.visit(`${spaceShipPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('SpaceShip');
    });

    it('should create an instance of SpaceShip', () => {
      cy.get(`[data-cy="name"]`).type('Georgia Coche').should('have.value', 'Georgia Coche');

      cy.get(`[data-cy="type"]`).select('CHEMICAL');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        spaceShip = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', spaceShipPageUrlPattern);
    });
  });
});
