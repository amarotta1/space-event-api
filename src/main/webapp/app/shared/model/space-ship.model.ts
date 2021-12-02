import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { IMission } from 'app/shared/model/mission.model';
import { SpaceShipThrusters } from 'app/shared/model/enumerations/space-ship-thrusters.model';

export interface ISpaceShip {
  id?: number;
  name?: string;
  type?: SpaceShipThrusters;
  manufacturer?: IManufacturer | null;
  missions?: IMission[] | null;
}

export const defaultValue: Readonly<ISpaceShip> = {};
