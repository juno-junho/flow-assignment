const {Model, DataTypes} = require('sequelize');

class User extends Model {
    static initiate(sequelize) { // 테이블에 대한 설정
        User.init({
            id: {
                type: DataTypes.BIGINT,
                allowNull: false,
                primaryKey: true,
                autoIncrement: true
            },
            role: {
                type: DataTypes.ENUM('ADMIN', 'GUEST'),
                allowNull: false,
            }
        }, {
            sequelize,
            modelName: 'User',
            tableName: 'users',
            timestamps: false,
        });
    }

    static associate(db) { // 다른 모델과의 관계
    }
}

module.exports = User;
