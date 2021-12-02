import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { getEntities as getManufacturers } from 'app/entities/manufacturer/manufacturer.reducer';
import { IMission } from 'app/shared/model/mission.model';
import { getEntities as getMissions } from 'app/entities/mission/mission.reducer';
import { getEntity, updateEntity, createEntity, reset } from './space-ship.reducer';
import { ISpaceShip } from 'app/shared/model/space-ship.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { SpaceShipThrusters } from 'app/shared/model/enumerations/space-ship-thrusters.model';

export const SpaceShipUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const manufacturers = useAppSelector(state => state.manufacturer.entities);
  const missions = useAppSelector(state => state.mission.entities);
  const spaceShipEntity = useAppSelector(state => state.spaceShip.entity);
  const loading = useAppSelector(state => state.spaceShip.loading);
  const updating = useAppSelector(state => state.spaceShip.updating);
  const updateSuccess = useAppSelector(state => state.spaceShip.updateSuccess);
  const spaceShipThrustersValues = Object.keys(SpaceShipThrusters);
  const handleClose = () => {
    props.history.push('/space-ship');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getManufacturers({}));
    dispatch(getMissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...spaceShipEntity,
      ...values,
      missions: mapIdList(values.missions),
      manufacturer: manufacturers.find(it => it.id.toString() === values.manufacturer.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'ION',
          ...spaceShipEntity,
          manufacturer: spaceShipEntity?.manufacturer?.id,
          missions: spaceShipEntity?.missions?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="spaceApp.spaceShip.home.createOrEditLabel" data-cy="SpaceShipCreateUpdateHeading">
            <Translate contentKey="spaceApp.spaceShip.home.createOrEditLabel">Create or edit a SpaceShip</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="space-ship-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('spaceApp.spaceShip.name')}
                id="space-ship-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('spaceApp.spaceShip.type')} id="space-ship-type" name="type" data-cy="type" type="select">
                {spaceShipThrustersValues.map(spaceShipThrusters => (
                  <option value={spaceShipThrusters} key={spaceShipThrusters}>
                    {translate('spaceApp.SpaceShipThrusters.' + spaceShipThrusters)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="space-ship-manufacturer"
                name="manufacturer"
                data-cy="manufacturer"
                label={translate('spaceApp.spaceShip.manufacturer')}
                type="select"
              >
                <option value="" key="0" />
                {manufacturers
                  ? manufacturers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('spaceApp.spaceShip.mission')}
                id="space-ship-mission"
                data-cy="mission"
                type="select"
                multiple
                name="missions"
              >
                <option value="" key="0" />
                {missions
                  ? missions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/space-ship" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SpaceShipUpdate;
