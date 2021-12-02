import dayjs from 'dayjs';
import { IMission } from 'app/shared/model/mission.model';
import { SpaceEventType } from 'app/shared/model/enumerations/space-event-type.model';

export interface ISpaceEvent {
  id?: number;
  name?: string;
  date?: string;
  description?: string;
  photoContentType?: string;
  photo?: string;
  type?: SpaceEventType;
  mission?: IMission | null;
}

export const defaultValue: Readonly<ISpaceEvent> = {};
