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

describe('SpaceEvent e2e test', () => {
  const spaceEventPageUrl = '/space-event';
  const spaceEventPageUrlPattern = new RegExp('/space-event(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const spaceEventSample = {
    name: 'Algerian web fallos',
    date: '2021-12-02',
    description: 'Re-contextualizado',
    photo: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    photoContentType: 'unknown',
    type: 'LANDING',
  };

  let spaceEvent: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/space-events+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/space-events').as('postEntityRequest');
    cy.intercept('DELETE', '/api/space-events/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (spaceEvent) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/space-events/${spaceEvent.id}`,
      }).then(() => {
        spaceEvent = undefined;
      });
    }
  });

  it('SpaceEvents menu should load SpaceEvents page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('space-event');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SpaceEvent').should('exist');
    cy.url().should('match', spaceEventPageUrlPattern);
  });

  describe('SpaceEvent page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(spaceEventPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SpaceEvent page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/space-event/new$'));
        cy.getEntityCreateUpdateHeading('SpaceEvent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceEventPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/space-events',
          body: spaceEventSample,
        }).then(({ body }) => {
          spaceEvent = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/space-events+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [spaceEvent],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(spaceEventPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SpaceEvent page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('spaceEvent');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceEventPageUrlPattern);
      });

      it('edit button click should load edit SpaceEvent page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SpaceEvent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceEventPageUrlPattern);
      });

      it('last delete button click should delete instance of SpaceEvent', () => {
        cy.intercept('GET', '/api/space-events/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('spaceEvent').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spaceEventPageUrlPattern);

        spaceEvent = undefined;
      });
    });
  });

  describe('new SpaceEvent page', () => {
    beforeEach(() => {
      cy.visit(`${spaceEventPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('SpaceEvent');
    });

    it('should create an instance of SpaceEvent', () => {
      cy.get(`[data-cy="name"]`).type('Borders Guapo open-source').should('have.value', 'Borders Guapo open-source');

      cy.get(`[data-cy="date"]`).type('2021-12-02').should('have.value', '2021-12-02');

      cy.get(`[data-cy="description"]`).type('grupo Gorro Refinado').should('have.value', 'grupo Gorro Refinado');

      cy.setFieldImageAsBytesOfEntity('photo', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="type"]`).select('LANDING');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        spaceEvent = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', spaceEventPageUrlPattern);
    });
  });
});
