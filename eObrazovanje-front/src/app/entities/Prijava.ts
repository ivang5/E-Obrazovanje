import { PredispitnaObaveza } from "./PredispitnaObaveza";
import { Student } from "./Student";

export class Prijava {
    id?:number;
    ocenjen: boolean;
    polozen: boolean;
    bodovi: number;
    predispitnaObaveza: PredispitnaObaveza | null;
    student: Student | null;
}