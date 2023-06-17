const express = require('express');
const app = express();

app.use(express.json());

app.get('/', (req, res) => {
    res.send('Hello There!');
});

app.post('/hello', (req, res)=>{
    console.log(req.body);
    res.send({message: 'Hello There!'});
});

app.listen(7771, () => {
    console.log('Server started on port: ', 7771);
});