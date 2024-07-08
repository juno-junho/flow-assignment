const {Model, DataTypes} = require('sequelize');

class CustomFileExtension extends Model {
    static initiate(sequelize) {
        CustomFileExtension.init({
            id: {
                type: DataTypes.BIGINT,
                allowNull: false,
                primaryKey: true,
                autoIncrement: true
            },
            fileExtension: {
                type: DataTypes.STRING(20),
                allowNull: false,
                validate: {
                    notNull: {
                        msg: "Extension cannot be null"
                    },
                    notEmpty: {
                        msg: "Extension cannot be empty"
                    },
                    isAlphanumeric: {
                        msg: "Extension must be alphanumeric"
                    },
                    len: {
                        args: [1, 20],
                        msg: "Extension must be no longer than 20 characters"
                    }
                },
                set(value) {
                    this.setDataValue('fileExtension', value.trim().toLowerCase());
                }
            },
            userId: {
                type: DataTypes.BIGINT,
                allowNull: false
            }
        }, {
            sequelize,
            modelName: 'CustomFileExtension',
            tableName: 'custom_file_extension',
            timestamps: true, // BaseTimeEntity를 상속받기 때문에 timestamps: true로 설정합니다.
            hooks: {
                beforeValidate: (customFileExtension, options) => {
                    if (customFileExtension.fileExtension && customFileExtension.fileExtension.includes(' ')) {
                        throw new Error('Extension cannot include spaces');
                    }
                }
            }
        });
    }

    isRestricted = () => true;

    getExtension = () => this.fileExtension.toLowerCase();

    static associate(db) { // 다른 모델과의 관계
    }
}

module.exports = CustomFileExtension;
