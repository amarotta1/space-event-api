import { currentPasswordSelector, newPasswordSelector } from '../../support/commands';

describe('E2E test contraseña segura', () => {
  beforeEach(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.loginAPI();
    cy.visit('http://localhost:8081/account/password');
  });

  it('Contaseña segura', () => {
    cy.get(currentPasswordSelector).type('admin');
    cy.get(newPasswordSelector).type('aecWCR473?!');
    cy.get('#strengthBar > :nth-child(5)').should('have.css', 'background-color', 'rgb(0, 255, 0)');
  });

  it('Contaseña insegura corta', () => {
    cy.get(currentPasswordSelector).type('admin');
    cy.get(newPasswordSelector).type('a');
    cy.get(newPasswordSelector).type('b');
    cy.get('#newPasswordLabel').click();
    cy.get('.invalid-feedback').contains('por lo menos 4 caracteres' || 'required to be at least 4 characters');
  });

  it('Contaseña insegura', () => {
    cy.get(currentPasswordSelector).type('admin');
    cy.get(newPasswordSelector).type('aaaaa');
    cy.get('#strengthBar > :nth-child(1)').should('have.css', 'background-color', 'rgb(255, 0, 0)');
  });
});
