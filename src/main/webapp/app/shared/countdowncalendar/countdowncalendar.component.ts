import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'jhi-countdowncalendar',
    templateUrl: './countdowncalendar.component.html',
    styleUrls: ['./countdowncalendar.component.css']
})
export class CountdowncalendarComponent {
    @Input()
    units: any;
    @Input()
    end: any;
    @Input()
    displayString = '';
    @Input()
    text: any;
    @Input()
    divider: any;
    @Output()
    reached: EventEmitter<Date> = new EventEmitter();
    display: any = [];
    displayNumbers: any = [];
    displayNumbers2: any = [];

    private wasReached = false;

    constructor() {
        setInterval(() => this._displayString(), 100);
    }

    _displayString() {
        if (typeof this.units === 'string') {
            this.units = this.units.split('|');
        }

        const givenDate: any = new Date(this.end);
        const now: any = new Date();

        const dateDifference: any = givenDate - now;
        const unitConstantForMillisecs = {
            year: 1000 * 60 * 60 * 24 * 7 * 4 * 12,
            month: 1000 * 60 * 60 * 24 * 7 * 4,
            weeks: 1000 * 60 * 60 * 24 * 7,
            days: 1000 * 60 * 60 * 24,
            hours: 1000 * 60 * 60,
            minutes: 1000 * 60,
            seconds: 1000
        };
        const unitsLeft = {};

        if (dateDifference < 100 && dateDifference > 0 && !this.wasReached) {
            this.wasReached = true;
            this.reached.next(now);
        }

        const lastUnit = this.units[this.units.length - 1];
        let returnText = '',
            returnNumbers = '',
            returnNumbers2 = '',
            totalMillisecsLeft = dateDifference,
            i,
            unit: any;
        for (i in this.units) {
            if (this.units.hasOwnProperty(i)) {
                unit = this.units[i].trim();
                if (unitConstantForMillisecs[unit.toLowerCase()] === false) {
                    // $interval.cancel(countDownInterval);
                    throw new Error('Cannot repeat unit: ' + unit);
                }
                if (unitConstantForMillisecs.hasOwnProperty(unit.toLowerCase()) === false) {
                    throw new Error(
                        'Unit: ' +
                            unit +
                            ' is not supported. Please use following units: year, month, weeks, days, hours, minutes, seconds, milliseconds'
                    );
                }

                unitsLeft[unit] = totalMillisecsLeft / unitConstantForMillisecs[unit.toLowerCase()];

                if (lastUnit === unit) {
                    unitsLeft[unit] = Math.ceil(unitsLeft[unit]);
                } else {
                    unitsLeft[unit] = Math.floor(unitsLeft[unit]);
                }
                totalMillisecsLeft -= unitsLeft[unit] * unitConstantForMillisecs[unit.toLowerCase()];
                unitConstantForMillisecs[unit.toLowerCase()] = false;

                returnNumbers += ' ' + unitsLeft[unit] + ' | ';
                returnNumbers2 += '' + (unitsLeft[unit] + 1) + '|';
                returnText += ' ' + unit;
            }
        }

        if (this.text === null || !this.text) {
            this.text = {
                Year: 'Year',
                Month: 'Month',
                Weeks: 'Weeks',
                Days: 'Days',
                Hours: 'Hours',
                Minutes: 'Minutes',
                Seconds: 'Seconds',
                MilliSeconds: 'Milliseconds'
            };
        }

        this.displayString = returnText
            .replace('Year', this.text.Year + ' | ')
            .replace('Month', this.text.Month + ' | ')
            .replace('Weeks', this.text.Weeks + ' | ')
            .replace('Days', this.text.Days + ' | ')
            .replace('Hours', this.text.Hours + ' | ')
            .replace('Minutes', this.text.Minutes + ' | ')
            .replace('Seconds', this.text.Seconds);

        this.displayNumbers = returnNumbers.split('|');
        this.displayNumbers2 = returnNumbers2.split('|');
        this.display = this.displayString.split('|');
    }

    flip() {
        console.log('blblblb');
    }
}
