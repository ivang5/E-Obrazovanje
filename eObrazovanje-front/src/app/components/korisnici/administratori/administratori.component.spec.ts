import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministratoriComponent } from './administratori.component';

describe('AdministratoriComponent', () => {
  let component: AdministratoriComponent;
  let fixture: ComponentFixture<AdministratoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministratoriComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministratoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
