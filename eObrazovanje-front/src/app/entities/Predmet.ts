import { Nastavnik } from "./Nastavnik";

export interface Predmet{
    id? : number | null;
    naziv : string;
    espb : number;
    predavaci: Nastavnik[];
}