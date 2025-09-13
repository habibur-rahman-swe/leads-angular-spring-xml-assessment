import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { EmployeeService } from '../services/employee.service';
import { first } from 'rxjs';

@Component({
  selector: 'app-update-employee',
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './update-employee.component.html',
  styleUrl: './update-employee.component.css',
  providers: [EmployeeService]
})
export class UpdateEmployeeComponent {
  employeeForm : FormGroup;
  employeeId!: number;

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) { 
    this.employeeForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      division: ['', Validators.required],
      building: ['', Validators.required],
      title: ['', Validators.required],
      room: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.employeeId = Number(this.route.snapshot.paramMap.get('employeeId'));
    if (this.employeeId) {
      this.employeeService.getEmployeeById(this.employeeId).subscribe((employee) => {
        // console.log(employee); // Check if data is received
        this.employeeForm.patchValue({
          firstName: employee.firstName,
          lastName: employee.lastName,
          division: employee.division,
          building: employee.building,
          title: employee.title,
          room: employee.room
        });
      });
    }
  }

  updateEmployee() {
    if (this.employeeForm.valid) {
      const updatedEmployee = { employeeId: this.employeeId, ...this.employeeForm.value };
      this.employeeService.updateEmployee(updatedEmployee).pipe(first()).subscribe(() => {
        this.router.navigate(['/employees']);
      });
    }
  }
  
}
