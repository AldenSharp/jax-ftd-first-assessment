import net from 'net'

const host = 'localhost'
const port = 667

let server

const openConnection = () => {
  server = net.createConnection(port, host, () => {
    return 0
  })
}

const closeConnection = () => {
  server.end()
}

const writeStr = (string) => server.write(string + '\n')

const writeObj = (key, object) => {
  openConnection()
  server.write(JSON.stringify({ key: object }) + '\n')
  closeConnection()
}

export const writeMessage = (command, content) => writeObj(
  'Message', {'command': command, 'content': content}
)

export function test () {
  openConnection()
  let output = JSON.stringify({'Message': {'command': 'heres the command', 'content': 'heres the content'}}) + '\n'
  server.write(output)
  closeConnection()
  return output
}

export const isRegistered = (username) => new Promise(
  function executor (resolve, reject) {
    let output
    let message = {'command': 'isRegistered', 'content': username}
    writeObj('Message', message)
    openConnection()
    server.on('data', d => output = d)
    closeConnection()
    resolve(output)
  }
)

export default {
  writeMessage,
  test,
  isRegistered
}
