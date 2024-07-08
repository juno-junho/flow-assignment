const express = require('express');
const path = require('path');
const morgan = require('morgan');
const multer = require('multer');
const extensionBlockRoutes = require('./api/extension-blocker/controllers/extension-block-controller');

const {sequelize} = require('./models')
require('dotenv').config();

const app = express();
app.set('port', process.env.PORT || 3000);

const upload = multer({dest: path.join(__dirname, 'public/uploads')});

sequelize.sync({force: false})
  .then(() => {
    console.log('데이터베이스 연결 성공');
  })
  .catch((err) => {
    console.error(err);
  });

// DB 설정
// const sequelize = new Sequelize(process.env.DB_NAME, process.env.DB_USER, process.env.DB_PASSWORD, {
// host: process.env.DB_HOST,
//   dialect: 'mysql',
//   logging: false
// });

// 미들웨어 설정
app.use(express.json());
app.use(express.urlencoded({extended: true}));
// app.use(express.static(path.join(__dirname, 'public')));
app.use(morgan('dev'));

// 라우터 설정
app.get('/', (req, res) => {
  console.log("Welcome to the Extension Blocker Node Server!");
  res.send('Welcome to the Extension Blocker Node Server!');
});
app.use('/api/v1/extension-blocks', extensionBlockRoutes);

app.use((req, res, next) => {
  const error = new Error(`${req.method} ${req.url} 라우터가 없습니다.`);
  error.status = 404;
  next(error);
});

app.use((err, req, res, next) => {
  res.locals.message = err.message;
  res.locals.error = process.env.NODE_ENV !== 'production' ? err : {};
  res.status(err.status || 500);
  res.json({
    message: err.message,
    error: err
  });
});
// 데이터베이스 연결 테스트

const assertDatabaseConnectionOk = async () => {
  try {
    await sequelize.authenticate();
    console.log('Database connection has been established successfully.');
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
}

// 서버 시작
const port = app.get('port');
// app.listen(port, async () => {
//   console.log(`Server is running on port ${port}`);
//   await assertDatabaseConnectionOk();
// });

app.listen(app.get('port'), () => {
  console.log(app.get('port'), '번 포트에서 대기 중');
});

module.exports = app;
