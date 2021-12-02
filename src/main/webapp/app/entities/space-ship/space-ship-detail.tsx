import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './space-ship.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpaceShipDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const spaceShipEntity = useAppSelector(state => state.spaceShip.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="spaceShipDetailsHeading">
          <Translate contentKey="spaceApp.spaceShip.detail.title">SpaceShip</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{spaceShipEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="spaceApp.spaceShip.name">Name</Translate>
            </span>
          </dt>
          <dd>{spaceShipEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="spaceApp.spaceShip.type">Type</Translate>
            </span>
          </dt>
          <dd>{spaceShipEntity.type}</dd>
          <dt>
            <Translate contentKey="spaceApp.spaceShip.manufacturer">Manufacturer</Translate>
          </dt>
          <dd>{spaceShipEntity.manufacturer ? spaceShipEntity.manufacturer.id : ''}</dd>
          <dt>
            <Translate contentKey="spaceApp.spaceShip.mission">Mission</Translate>
          </dt>
          <dd>
            {spaceShipEntity.missions
              ? spaceShipEntity.missions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {spaceShipEntity.missions && i === spaceShipEntity.missions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/space-ship" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/space-ship/${spaceShipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpaceShipDetail;
