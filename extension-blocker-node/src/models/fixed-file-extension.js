const { Model, DataTypes } = require('sequelize');
const fileExtension = require('./file-extension');

class FixedFileExtension extends Model {
    static initiate(sequelize) {
        FixedFileExtension.init({
            id: {
                type: DataTypes.BIGINT,
                allowNull: false,
                primaryKey: true,
                autoIncrement: true
            },
            fileExtension: {
                type: DataTypes.STRING(20),
                allowNull: false,
                get() {
                    const rawValue = this.getDataValue('fileExtension');
                    return rawValue ? rawValue.toLowerCase() : null;
                },
                set(value) {
                    if (!fileExtension[value.toUpperCase()]) {
                        throw new Error('Invalid File Extension!');
                    }
                    this.setDataValue('fileExtension', value.trim().toUpperCase());
                }
            },
            restricted: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: true
            },
            userId: {
                type: DataTypes.BIGINT,
                allowNull: false
            }
        }, {
            sequelize,
            modelName: 'FixedFileExtension',
            tableName: 'fixed_file_extension',
            timestamps: true,
        });
    }

    isRestricted = () => true;

    getExtension = () => this.fileExtension.toLowerCase();

    static associate(db) {
    }// 다른 모델과의 관계

}

module.exports = FixedFileExtension;
