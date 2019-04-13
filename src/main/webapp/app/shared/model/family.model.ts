import { IAccesscode } from 'app/shared/model//accesscode.model';

export interface IFamily {
    id?: number;
    familyName?: string;
    streetName?: string;
    houseNumber?: string;
    zipCode?: number;
    city?: string;
    accesscode?: IAccesscode;
}

export class Family implements IFamily {
    constructor(
        public id?: number,
        public familyName?: string,
        public streetName?: string,
        public houseNumber?: string,
        public zipCode?: number,
        public city?: string,
        public accesscode?: IAccesscode
    ) {}
}
