import { IPartyPart } from 'app/shared/model//party-part.model';

export interface IAccesscode {
    id?: number;
    code?: string;
    willAttend?: boolean;
    partyParts?: IPartyPart[];
}

export class Accesscode implements IAccesscode {
    constructor(public id?: number, public code?: string, public willAttend?: boolean, public partyParts?: IPartyPart[]) {
        this.willAttend = this.willAttend || false;
    }
}
