import { ISpaceShip } from 'app/shared/model/space-ship.model';

export interface IMission {
  id?: number;
  name?: string;
  description?: string | null;
  spaceShips?: ISpaceShip[] | null;
}

export const defaultValue: Readonly<IMission> = {};
