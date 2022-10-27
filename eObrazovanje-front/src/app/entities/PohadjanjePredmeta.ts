import { Predmet } from "./Predmet";
import { Student } from "./Student";

export class PohadjanjePredmeta{
    id? : number;
    startDate : string;
    endDate : string;
    studenti : Student[];
    predmet : Predmet | undefined;
}