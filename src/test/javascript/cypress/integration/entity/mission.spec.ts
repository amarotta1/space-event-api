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

describe('Mission e2e test', () => {
  const missionPageUrl = '/mission';
  const missionPageUrlPattern = new RegExp('/mission(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const missionSample = { name: 'Account 24/7 driver' };

  let mission: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/missions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/missions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/missions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (mission) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/missions/${mission.id}`,
      }).then(() => {
        mission = undefined;
      });
    }
  });

  it('Missions menu should load Missions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('mission');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Mission').should('exist');
    cy.url().should('match', missionPageUrlPattern);
  });

  describe('Mission page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(missionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Mission page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/mission/new$'));
        cy.getEntityCreateUpdateHeading('Mission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', missionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/missions',
          body: missionSample,
        }).then(({ body }) => {
          mission = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/missions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [mission],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(missionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Mission page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('mission');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', missionPageUrlPattern);
      });

      it('edit button click should load edit Mission page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Mission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', missionPageUrlPattern);
      });

      it('last delete button click should delete instance of Mission', () => {
        cy.intercept('GET', '/api/missions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('mission').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', missionPageUrlPattern);

        mission = undefined;
      });
    });
  });

  describe('new Mission page', () => {
    beforeEach(() => {
      cy.visit(`${missionPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Mission');
    });

    it('should create an instance of Mission', () => {
      cy.get(`[data-cy="name"]`).type('Planificador firewall back-end').should('have.value', 'Planificador firewall back-end');

      cy.get(`[data-cy="description"]`).type('a Dollar THX').should('have.value', 'a Dollar THX');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        mission = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', missionPageUrlPattern);
    });
  });
});
