import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './space-ship.reducer';
import { ISpaceShip } from 'app/shared/model/space-ship.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpaceShip = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const spaceShipList = useAppSelector(state => state.spaceShip.entities);
  const loading = useAppSelector(state => state.spaceShip.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="space-ship-heading" data-cy="SpaceShipHeading">
        <Translate contentKey="spaceApp.spaceShip.home.title">Space Ships</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="spaceApp.spaceShip.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="spaceApp.spaceShip.home.createLabel">Create new Space Ship</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {spaceShipList && spaceShipList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="spaceApp.spaceShip.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceShip.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceShip.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceShip.manufacturer">Manufacturer</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceShip.mission">Mission</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {spaceShipList.map((spaceShip, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${spaceShip.id}`} color="link" size="sm">
                      {spaceShip.id}
                    </Button>
                  </td>
                  <td>{spaceShip.name}</td>
                  <td>
                    <Translate contentKey={`spaceApp.SpaceShipThrusters.${spaceShip.type}`} />
                  </td>
                  <td>
                    {spaceShip.manufacturer ? (
                      <Link to={`manufacturer/${spaceShip.manufacturer.id}`}>{spaceShip.manufacturer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {spaceShip.missions
                      ? spaceShip.missions.map((val, j) => (
                          <span key={j}>
                            <Link to={`mission/${val.id}`}>{val.id}</Link>
                            {j === spaceShip.missions.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${spaceShip.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${spaceShip.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${spaceShip.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="spaceApp.spaceShip.home.notFound">No Space Ships found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SpaceShip;
