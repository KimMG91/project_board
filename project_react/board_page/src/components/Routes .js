import React from 'react';
import { Router, Routes, Route } from 'react-router-dom';
import Login from '../pages/Login';
import SignUp from '../pages/SignUp';
import MyInfo from '../pages/MyInfo';

class AppRoutes extends React.Component {
  render() {
    return (
        <Router>
          <Routes>
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
            <Route path="/myinfo" element={<MyInfo />} />
          </Routes>
        </Router>
      );
    }
  }

export default AppRoutes;

// `<Router>` :  <BrowserRouter>의 줄임말이며, 최상위 컴포넌트를 감싸준다.
// `<Switch>` : Switch 되면서 Route마다 조건을 검사하며 페이지를 렌더링한다. 한 페이지가 렌더링 됐을 때는 다른 Route는 렌더하지 않는다.
// (경로가 /일 때, /하위일 때의 모든 결과가 화면에 출력되지 않도록 한다.)
// `<Route>` : <Route> 태그 안에는 라우팅할 경로와 컴포넌트에 대한 속성값을 준다.

// `ex. <Route exact path="/main" component={Main} />`
// exact : exact 속성이 필요한 이유는 명확한 경로를 렌더링 시켜주기 위함이며,
// 이 속성이 없을 경우 하위 경로의 페이지가 같이 나올 수 있다.
// path : 이동할 경로의 URL의 경로 이름을 설정해준다.
// component : 이동할 component를 설정해준다.
