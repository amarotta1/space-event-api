describe('Cambiar lenguaje con login por API', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.loginAPI();
    cy.visit('');
  });

  it('Test cambiar idioma al Español', () => {
    cy.get(':nth-child(4) > .d-flex').click();

    cy.get('[value="es"]').click();

    cy.get('.d-flex > :nth-child(2) > span').contains('Inicio');
    cy.get('[data-cy=entity] > .d-flex > span').contains('Entidades');
    cy.get('[data-cy=adminMenu] > .d-flex > span').contains('Administración');
    cy.get(':nth-child(4) > .d-flex > span').contains('Español');
    cy.get('[data-cy=accountMenu] > .d-flex > span').contains('Cuenta');
  });

  it('Test cambiar idioma al Ingles', () => {
    cy.get(':nth-child(4) > .d-flex').click();

    cy.get('[value="en"]').click();

    cy.get('.d-flex > :nth-child(2) > span').contains('Home');
    cy.get('[data-cy=entity] > .d-flex > span').contains('Entities');
    cy.get('[data-cy=adminMenu] > .d-flex > span').contains('Administration');
    cy.get(':nth-child(4) > .d-flex > span').contains('English');
    cy.get('[data-cy=accountMenu] > .d-flex > span').contains('Account');
  });
});
