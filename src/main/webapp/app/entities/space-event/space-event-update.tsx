import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMission } from 'app/shared/model/mission.model';
import { getEntities as getMissions } from 'app/entities/mission/mission.reducer';
import { getEntity, updateEntity, createEntity, reset } from './space-event.reducer';
import { ISpaceEvent } from 'app/shared/model/space-event.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { SpaceEventType } from 'app/shared/model/enumerations/space-event-type.model';

export const SpaceEventUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const missions = useAppSelector(state => state.mission.entities);
  const spaceEventEntity = useAppSelector(state => state.spaceEvent.entity);
  const loading = useAppSelector(state => state.spaceEvent.loading);
  const updating = useAppSelector(state => state.spaceEvent.updating);
  const updateSuccess = useAppSelector(state => state.spaceEvent.updateSuccess);
  const spaceEventTypeValues = Object.keys(SpaceEventType);
  const handleClose = () => {
    props.history.push('/space-event');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...spaceEventEntity,
      ...values,
      mission: missions.find(it => it.id.toString() === values.mission.toString()),
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
          type: 'LAUNCH',
          ...spaceEventEntity,
          mission: spaceEventEntity?.mission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="spaceApp.spaceEvent.home.createOrEditLabel" data-cy="SpaceEventCreateUpdateHeading">
            <Translate contentKey="spaceApp.spaceEvent.home.createOrEditLabel">Create or edit a SpaceEvent</Translate>
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
                  id="space-event-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('spaceApp.spaceEvent.name')}
                id="space-event-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('spaceApp.spaceEvent.date')}
                id="space-event-date"
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('spaceApp.spaceEvent.description')}
                id="space-event-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('spaceApp.spaceEvent.photo')}
                id="space-event-photo"
                name="photo"
                data-cy="photo"
                isImage
                accept="image/*"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('spaceApp.spaceEvent.type')} id="space-event-type" name="type" data-cy="type" type="select">
                {spaceEventTypeValues.map(spaceEventType => (
                  <option value={spaceEventType} key={spaceEventType}>
                    {translate('spaceApp.SpaceEventType.' + spaceEventType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="space-event-mission"
                name="mission"
                data-cy="mission"
                label={translate('spaceApp.spaceEvent.mission')}
                type="select"
              >
                <option value="" key="0" />
                {missions
                  ? missions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/space-event" replace color="info">
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

export default SpaceEventUpdate;
