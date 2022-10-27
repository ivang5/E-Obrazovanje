
import { Predmet } from "./Predmet";

export class PredispitnaObaveza {
    id?:number;
    tipPredispitneObaveze: string;
    datum: string;
    sala: string;
    predmet: Predmet | null;
}