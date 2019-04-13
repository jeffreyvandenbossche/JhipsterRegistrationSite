import { IFamily } from 'app/shared/model//family.model';
import { IPartyPart } from 'app/shared/model//party-part.model';

export interface IPerson {
    id?: number;
    firstName?: string;
    familyName?: string;
    foodRestriction?: string;
    family?: IFamily;
    partyParts?: IPartyPart[];
}

export class Person implements IPerson {
    constructor(
        public id?: number,
        public firstName?: string,
        public familyName?: string,
        public foodRestriction?: string,
        public family?: IFamily,
        public partyParts?: IPartyPart[]
    ) {}
}
