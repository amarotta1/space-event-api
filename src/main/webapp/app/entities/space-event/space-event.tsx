import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './space-event.reducer';
import { ISpaceEvent } from 'app/shared/model/space-event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpaceEvent = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const spaceEventList = useAppSelector(state => state.spaceEvent.entities);
  const loading = useAppSelector(state => state.spaceEvent.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="space-event-heading" data-cy="SpaceEventHeading">
        <Translate contentKey="spaceApp.spaceEvent.home.title">Space Events</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="spaceApp.spaceEvent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="spaceApp.spaceEvent.home.createLabel">Create new Space Event</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {spaceEventList && spaceEventList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.photo">Photo</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="spaceApp.spaceEvent.mission">Mission</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {spaceEventList.map((spaceEvent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${spaceEvent.id}`} color="link" size="sm">
                      {spaceEvent.id}
                    </Button>
                  </td>
                  <td>{spaceEvent.name}</td>
                  <td>{spaceEvent.date ? <TextFormat type="date" value={spaceEvent.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{spaceEvent.description}</td>
                  <td>
                    {spaceEvent.photo ? (
                      <div>
                        {spaceEvent.photoContentType ? (
                          <a onClick={openFile(spaceEvent.photoContentType, spaceEvent.photo)}>
                            <img src={`data:${spaceEvent.photoContentType};base64,${spaceEvent.photo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {spaceEvent.photoContentType}, {byteSize(spaceEvent.photo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`spaceApp.SpaceEventType.${spaceEvent.type}`} />
                  </td>
                  <td>{spaceEvent.mission ? <Link to={`mission/${spaceEvent.mission.id}`}>{spaceEvent.mission.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${spaceEvent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${spaceEvent.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${spaceEvent.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="spaceApp.spaceEvent.home.notFound">No Space Events found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SpaceEvent;
