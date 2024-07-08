const {Model, DataTypes} = require('sequelize');

const MAX_EXTENSION_LENGTH = 20;
const ONLY_ALPHANUMERIC = "^[a-zA-Z0-9]+$";

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
                field: 'file_extension',
                set(value) {
                    this.setDataValue('fileExtension', value.trim().toLowerCase());
                }
            },
            userId: {
                type: DataTypes.BIGINT,
                allowNull: false,
                field: 'user_id'
            },
            createdAt: {
                field: 'created_at',
                type: DataTypes.DATE,
                allowNull: false,
            },
            updatedAt: {
                field: 'last_modified_at',
                type: DataTypes.DATE,
                allowNull: false,
            }
        }, {
            sequelize,
            modelName: 'CustomFileExtension',
            tableName: 'custom_file_extension',
            timestamps: true, // BaseTimeEntity를 상속받기 때문에 timestamps: true로 설정합니다.
            underscored: true,
            hooks: {
                beforeValidate: (customFileExtension, options) => {
                    if (customFileExtension.fileExtension == null || customFileExtension.fileExtension.trim().length === 0) {
                        throw new Error('확장자가 비어있거나 null입니다.');
                    }
                    if (customFileExtension.fileExtension.length > MAX_EXTENSION_LENGTH) {
                        throw new Error('파일의 확장자가 최대 길이를 초과했습니다.');
                    }
                    if (customFileExtension.fileExtension.trim().includes(' ')) {
                        throw new Error('파일의 확장자에 공백이 포함되어 있습니다.');
                    }
                    if(!customFileExtension.fileExtension.match(ONLY_ALPHANUMERIC)) {
                        throw new Error('파일의 확장자는 알파벳과 숫자만 가능합니다.');
                    }
                }
            }
        });
    }

    isRestricted = () => true;

    getExtension = () => this.fileExtension.toLowerCase();

    static associate(db) {} // 다른 모델과의 관계

}

module.exports = CustomFileExtension;
