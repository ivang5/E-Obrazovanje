import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { StudentiComponent } from './components/korisnici/studenti/studenti.component';
import { NastavniciComponent } from './components/korisnici/nastavnici/nastavnici.component';
import { JwtModule } from '@auth0/angular-jwt';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AdministratoriComponent } from './components/korisnici/administratori/administratori.component';
import { AdministratorComponent } from './components/korisnici/administrator/administrator.component';
import { StudentComponent } from './components/korisnici/student/student.component';
import { NastavnikComponent } from './components/korisnici/nastavnik/nastavnik.component';
import { ProfilComponent } from './components/korisnici/profil/profil.component';

import { UplataComponent } from './components/uplata/uplata.component';
import { PredmetComponent } from './components/predmet/predmet.component';
import { PredmetiComponent } from './components/predmeti/predmeti.component';
import { DokumentiComponent } from './components/dokumenti/dokumenti.component';
import { DokumentComponent } from './components/dokument/dokument.component';
import { UplateComponent } from './components/uplate/uplate.component';
import { PredispitneObavezeComponent } from './components/predispitne-obaveze/predispitne-obaveze.component';
import { PrijaveComponent } from './components/prijave/prijave.component';
import { PohadjanjePredmetaComponent } from './components/pohadjanje-predmeta/pohadjanje-predmeta.component';

const appRoutes: Routes = [
  { path: '', component: ProfilComponent },
  { path: 'login', component: LoginComponent },
  { path: 'administratori', component: AdministratoriComponent },
  { path: 'administratori/:korisnickoIme', component: AdministratorComponent },
  { path: 'nastavnici', component: NastavniciComponent },
  { path: 'nastavnici/:korisnickoIme', component: NastavnikComponent },
  { path: 'studenti', component: StudentiComponent },
  { path: 'studenti/:korisnickoIme', component: StudentComponent },
  { path: 'predmeti', component: PredmetiComponent },
  { path: 'predmeti/:id', component: PredmetComponent },
  { path: 'pohadjanjePredmeta/:id', component: PohadjanjePredmetaComponent },
  { path: 'dokumenti', component: DokumentiComponent },
  { path: 'dokumenti/:id', component: DokumentComponent },
  { path: 'uplate', component: UplateComponent },
  { path: 'uplate/:id', component: UplataComponent },
  { path: 'predispitne-obaveze', component: PredispitneObavezeComponent },
  { path: 'prijave', component: PrijaveComponent },
  { path: 'rezultati', component: PrijaveComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '/404' },
];

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    ProfilComponent,
    LoginComponent,
    AdministratoriComponent,
    AdministratorComponent,
    NastavniciComponent,
    NastavnikComponent,
    StudentiComponent,
    StudentComponent,
    NavbarComponent,
    FooterComponent,
    NotFoundComponent,
    DokumentiComponent,
    UplataComponent,
    PredmetComponent,
    PredmetiComponent,
    DokumentComponent,
    UplateComponent,
    PredispitneObavezeComponent,
    PrijaveComponent,
    PohadjanjePredmetaComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, { enableTracing: true }),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ['localhost:8080'],
        disallowedRoutes: ['http://localhost:8080/login/'],
        skipWhenExpired: true,
      },
    }),
    NgMultiSelectDropDownModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
