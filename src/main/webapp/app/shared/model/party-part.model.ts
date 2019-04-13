import { IPerson } from 'app/shared/model//person.model';
import { IAccesscode } from 'app/shared/model//accesscode.model';

export interface IPartyPart {
    id?: number;
    partName?: string;
    people?: IPerson[];
    accesscodes?: IAccesscode[];
}

export class PartyPart implements IPartyPart {
    constructor(public id?: number, public partName?: string, public people?: IPerson[], public accesscodes?: IAccesscode[]) {}
}
