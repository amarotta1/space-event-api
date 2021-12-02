import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SpaceEvent from './space-event';
import Mission from './mission';
import SpaceShip from './space-ship';
import Manufacturer from './manufacturer';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}space-event`} component={SpaceEvent} />
      <ErrorBoundaryRoute path={`${match.url}mission`} component={Mission} />
      <ErrorBoundaryRoute path={`${match.url}space-ship`} component={SpaceShip} />
      <ErrorBoundaryRoute path={`${match.url}manufacturer`} component={Manufacturer} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
