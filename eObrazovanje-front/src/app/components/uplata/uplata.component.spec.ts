import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UplataComponent } from './uplata.component';

describe('UplataComponent', () => {
  let component: UplataComponent;
  let fixture: ComponentFixture<UplataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UplataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UplataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
