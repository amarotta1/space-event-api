describe('E2E test Activar y desactivar usuarios', () => {
  beforeEach(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.loginAPI();
    cy.visit('http://localhost:8081/admin/user-management');
  });

  it('Activar y desactivar usuario por defecto', () => {
    cy.get('#user > :nth-child(4) > .btn > span').then($text => {
      const txt = $text.text();

      if (txt === ('Activado' || 'Activated')) {
        cy.get('#user > :nth-child(4) > .btn').click();
        cy.get('#user > .text-end > .btn-group > .btn-primary').click();
        cy.get('#activated').should('not.be.checked');
      } else {
        cy.get('#user > :nth-child(4) > .btn').click();
        cy.get('#user > .text-end > .btn-group > .btn-primary').click();
        cy.get('#activated').should('be.checked');
      }
    });
  });
});
