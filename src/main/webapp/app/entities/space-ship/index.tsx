import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SpaceShip from './space-ship';
import SpaceShipDetail from './space-ship-detail';
import SpaceShipUpdate from './space-ship-update';
import SpaceShipDeleteDialog from './space-ship-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SpaceShipUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SpaceShipUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SpaceShipDetail} />
      <ErrorBoundaryRoute path={match.url} component={SpaceShip} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SpaceShipDeleteDialog} />
  </>
);

export default Routes;
