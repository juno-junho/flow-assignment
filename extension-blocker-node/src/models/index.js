const Sequelize = require('sequelize');

// model
const User = require('./user');
const CustomFileExtension = require('./custom-file-extension');
const FixedFileExtension = require('./fixed-file-extension');
const File = require('./file');
const FileExtension = require('./file-extension');


const env = process.env.NODE_ENV || 'development';
const config = require('../config/config')[env];
const db = {};
const sequelize = new Sequelize(config.database, config.username, config.password, config);

db.sequelize = sequelize;

User.initiate(sequelize);
CustomFileExtension.initiate(sequelize);
FixedFileExtension.initiate(sequelize);
File.initiate(sequelize);

User.associate(db);
CustomFileExtension.associate(db);
FixedFileExtension.associate(db);
File.associate(db);

module.exports = db;
