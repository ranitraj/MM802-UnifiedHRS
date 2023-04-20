<template>
    <form @submit.prevent="handleSubmit">
      <label>
        Name:
        <input type="text" v-model="name" required>
      </label>
      <br>
      <label>
        Wallet:
        <input type="Wallet" v-model="text" required>
      </label>
      <br>
      <label>
        Service Used:
        <input type="service" v-model="text" required>
      </label>
      <br>
      <label>
        Billed amount in Eth:
        <input type="cost" v-model="text" required>
      </label>
      <br>
      <button type="submit">Submit</button>
    </form>
  </template>
  
<script>
// import { ethers } from 'ethers'
const updateRecord = "0xe7f1725E7734CE288F8367e1Bb143E90bb3F0512"
export default {
name: 'Form',
data() {
    return {
    name: '',
    wallet: '',
    service: '',
    cost: ''
    }
},
methods: {
    handleSubmit() {
      if (typeof window.ethereum !== 'undefined') {
        requestAccount()
        const provider = new ethers.providers.Web3Provider(window.ethereum);
        const signer = provider.getSigner();
        const contract = new ethers.Contract(updateRecord, Token.abi, signer);
        const transaction = contract.transfer(this.wallet);
        transaction.wait();
        console.log(`${this.wallet} account updated`);
      }
    }
  }
}
</script>
  