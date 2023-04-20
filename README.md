1. Start the local test node

```sh
npx hardhat node
```

2. Deploy the contract

```sh
npx hardhat run scripts/deploy.js --network localhost
```

3. Update __src/App.js__ with the values of your contract addresses (`greeterAddress` and `tokenAddress`)

4. Run the app

```sh
npm start
```