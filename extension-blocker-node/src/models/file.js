const {Model, DataTypes} = require('sequelize');

class File extends Model {
    static initiate(sequelize) {
        File.init({
            id: {
                type: DataTypes.BIGINT,
                allowNull: false,
                primaryKey: true,
                autoIncrement: true
            },
            uploadFileName: {
                type: DataTypes.STRING,
                allowNull: false,
            },
            generatedFileName: {
                type: DataTypes.STRING,
                allowNull: false,
            },
            fileExtension: {
                type: DataTypes.STRING,
                allowNull: false,
            },
            userId: {
                type: DataTypes.BIGINT,
                allowNull: false
            }
        }, {
            sequelize,
            modelName: 'File',
            tableName: 'file',
            timestamps: true
        });
    }
    static associate(db) {}
}

module.exports = File;
