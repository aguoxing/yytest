import request from '@/utils/request'

export function listUser(query) {
    return request({
        url: '/test/user/list',
        method: 'get',
        params: query
    })
}

export function getUser(userId) {
    return request({
        url: '/test/user/' + userId,
        method: 'get'
    })
}

export function addUser(data) {
    return request({
        url: '/test/user',
        method: 'post',
        data: data
    })
}

export function updateUser(data) {
    return request({
        url: '/test/user',
        method: 'put',
        data: data
    })
}

export function deleteUser(id) {
    return request({
        url: '/test/user/' + id,
        method: 'delete',
    })
}
