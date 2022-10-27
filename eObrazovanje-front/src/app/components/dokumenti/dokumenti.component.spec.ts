import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumentiComponent } from './dokumenti.component';

describe('DokumentComponent', () => {
  let component: DokumentiComponent;
  let fixture: ComponentFixture<DokumentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DokumentiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DokumentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
