import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/space-event">
      <Translate contentKey="global.menu.entities.spaceEvent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/mission">
      <Translate contentKey="global.menu.entities.mission" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/space-ship">
      <Translate contentKey="global.menu.entities.spaceShip" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/manufacturer">
      <Translate contentKey="global.menu.entities.manufacturer" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
