import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './manufacturer.reducer';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Manufacturer = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const manufacturerList = useAppSelector(state => state.manufacturer.entities);
  const loading = useAppSelector(state => state.manufacturer.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="manufacturer-heading" data-cy="ManufacturerHeading">
        <Translate contentKey="spaceApp.manufacturer.home.title">Manufacturers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="spaceApp.manufacturer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="spaceApp.manufacturer.home.createLabel">Create new Manufacturer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {manufacturerList && manufacturerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="spaceApp.manufacturer.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.manufacturer.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {manufacturerList.map((manufacturer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${manufacturer.id}`} color="link" size="sm">
                      {manufacturer.id}
                    </Button>
                  </td>
                  <td>{manufacturer.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${manufacturer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${manufacturer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${manufacturer.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="spaceApp.manufacturer.home.notFound">No Manufacturers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Manufacturer;
