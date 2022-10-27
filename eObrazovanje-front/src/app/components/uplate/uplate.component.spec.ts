import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UplateComponent } from './uplate.component';

describe('UplateComponent', () => {
  let component: UplateComponent;
  let fixture: ComponentFixture<UplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UplateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
