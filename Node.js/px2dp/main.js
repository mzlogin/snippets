const readline = require('readline')

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

const factor = 3.0;

function ask() {
  rl.question('Input PXs: ', (answer) => {
    if (answer == 'quit') {
      rl.close();
      return;
    }
    console.log('DPs is: ', Math.floor(parseInt(answer,10)/factor+0.5));
    ask();
  });
}

ask();
