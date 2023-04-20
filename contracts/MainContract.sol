// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;
pragma experimental ABIEncoderV2;

contract SimpleStorage {
  uint256 value;

  function read() public view returns (uint256) {
    return value;
  }

  function write(uint256 newValue) public {
    value = newValue;
  }
}

contract Helin {

  struct Hospital{
    address hospitalId;
    bytes32 hospitalName;
    uint128 departmentId;
    bytes32 departmentName;
}

  struct Insurance {
    address insuranceId;
    uint128 policyId;
    bytes32 insuranceName;
    bytes32 policyName;
}

  struct Bill {
    address patientId;
    uint128 billAmount;
    uint256 timeAdded;
    Hospital hospital;
  }

  struct Patient {
    address patientId;
    bytes32 patientName;
    Insurance[] insurances;
    Bill[] bills;
  }


  mapping (address => Patient) public patients;
  mapping (address => Bill) public bills;
  mapping (address => Hospital) public hospitals;
  mapping (address => Insurance) public insurances;

  event PatientAdded(address patientId);
  event InsuranceAdded(address insuranceId);
  event HospitalAdded(address hospitalId);
  event BillAdded(address patientId);
  event InsuranceAdded(address insuranceId, address patientId);


  modifier senderExists {
    require(hospitals[msg.sender].hospitalId == msg.sender || patients[msg.sender].patientId == msg.sender, "sender does not exist");
    _;
  }

  modifier senderIsInsurance {
    require(insurances[msg.sender].insuranceId == msg.sender, "Sender is not a an Insurance");
    _;
  }

  modifier senderIsPatient {
    require(patients[msg.sender].patientId == msg.sender, "Sender is not a patient");
    _;
  }

  modifier senderIsHospital {
    require(hospitals[msg.sender].hospitalId == msg.sender, "Sender is not Hospital");
    _;
  }

  function addPatient(address _patientId) public senderIsHospital {
    require(patients[_patientId].patientId != _patientId, "This patient already exists.");
    patients[_patientId].patientId = _patientId;
    emit PatientAdded(_patientId);
  }

  function addInsurance(address _insuranceId) public senderIsPatient {
    require(insurances[_insuranceId].insuranceId != _insuranceId, "This insurance already exists.");
    insurances[_insuranceId].insuranceId = _insuranceId;
    emit InsuranceAdded(_insuranceId);
  }

  function addHospital(address _hospitalId) public {
    require(hospitals[_hospitalId].hospitalId != _hospitalId, "This hospital already exists.");
    patients[_hospitalId].patientId = _hospitalId;
    emit HospitalAdded(_hospitalId);
  }

  function addBillToPatient(address _patientId) public senderIsHospital {
    patients[_patientId].bills.push(bills[_patientId]);
    emit BillAdded(_patientId);
  }

  function addInsuranceToPatient(address _patientId, address _insuranceId) public senderIsHospital {
    patients[_patientId].insurances.push(insurances[_insuranceId]);
    emit InsuranceAdded(_insuranceId, _patientId);
  }

  function getSenderRole() public view returns (string memory) {
    if (hospitals[msg.sender].hospitalId == msg.sender) {
      return "hospital";
    } else if (patients[msg.sender].patientId == msg.sender) {
      return "patient";
    }
    else if (insurances[msg.sender].insuranceId == msg.sender) {
      return "insurance";
    } else {
      return "unknown";
    }
  }
}