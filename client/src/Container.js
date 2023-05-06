import React, { useEffect, useState } from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import { BsStarFill, BsStar } from "react-icons/bs"; // Bootstrap icons for stars

import "./Container.css"; // CSS 파일 import
import { Link } from "react-router-dom";

function Container(props) {
  const [restaurantInfo, setRestaurantInfo] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [showModal, setShowModal] = useState(false); // State for modal
  const [rating, setRating] = useState(0); // State for rating
  const [comment, setComment] = useState("");
  const [comments, setComments] = useState([]);
  const [isEdit, setIsEdit] = useState(false)
  const [targetComment, setTargetComment] = useState([]) //삭제 수정할 댓글
  /*
  //모달 창 (특정 식당 눌렀을 때) 그 식당에 관련된 댓글을 뽑는 로직
  useEffect(() => {
    // showModal 값이 변경될 때마다 서버에서 댓글 데이터를 조회하는 로직
    const fetchComments = async () => {
      try {
        const response = await axios.get(`/comments/${selectedRestaurant.id}`); // 예시: 댓글 데이터를 가져오는 API 호출
        console.log(response);
        // setComments(response.data); // 가져온 댓글 데이터를 state에 설정
      } catch (error) {
        console.error("Failed to fetch comments", error);
      }
    };

    if (showModal) {
      fetchComments(); // showModal 값이 true일 때만 댓글 데이터를 가져오는 함수 호출
    }
  }, [showModal]); // restaurant.id와 showModal 값이 변경될 때마다 useEffect가 호출되도록 설정
  */

  //시작하자마자 전채 댓글 가져오기
  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await axios.get("/comments"); // 예시: 댓글 데이터를 가져오는 API 호출
        setComments(response.data); // 가져온 댓글 데이터를 state에 설정
      } catch (error) {
        console.error("Failed to fetch comments", error);
      }
    };
    fetchComments();
  }, [])


  const saveRestaurantInfo = async (data) => {
    try {
      // 전체 식당 정보를 서버로 전송할 데이터 객체 생성
      const restaurantList = data.map((restaurant) => ({
        name: restaurant.place_name, // 식당 이름
        address: restaurant.address_name, // 식당 주소
        comments: [],
        restaurantId: restaurant.id,
      }));
      // POST 요청을 통해 전체 식당 정보를 서버에 저장
      await axios.post("/api/restaurant/batch", restaurantList);
    } catch (error) {
      console.error("식당 정보 저장 실패", error);
    }
  };

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };
  const handleCommentUpdate = () => {
    if (!comment && !rating) {
      window.alert("댓글과 별점을 모두 입력해주세요.");
      return;
    }
    if (rating === 0) {
      window.alert("별점은 1점 이상 입력해주세요.");
      return;
    }
    if (!comment) {
      window.alert("댓글을 입력해주세요");
      return;
    }
    // POST 요청을 보낼 URL 설정
    const url = `/comments/update/${targetComment.commentId}`
    // Axios를 사용하여 POST 요청 보내기
    axios.post(url, {
      comment: comment,
      rating: rating
    })
      .then((response) => {
        // 서버로부터 응답이 성공적으로 받아졌을 경우의 처리
        // 예: 댓글이 성공적으로 저장되었다는 알림을 표시하거나, 댓글 목록을 업데이트함
        //여기서 필터로 걸러주고, 저장해야할듯
        const updatedComments = comments.map(comment => {
          if (comment.commentId === response.data.commentId) {
            return {
              ...comment,
              comment: response.data.comment,
              rating: response.data.rating
            };
          }
          return comment;
        });
        setComments(updatedComments)
        setRating(0)
        setComment("")
      })
      .catch((error) => {
        // 서버로부터 응답이 실패한 경우의 처리
        // 예: 에러 메시지를 표시하거나, 에러 상황을 처리함
        console.error("댓글 수정에 실패하였습니다.", error);
      });
    setIsEdit(false)
  }
  const handleCommentSave = () => {
    // 댓글 내용과 별점을 가져옴
    // 댓글 데이터 객체 생성
    if (!comment && !rating) {
      window.alert("댓글과 별점을 모두 입력해주세요.");
      return;
    }
    if (rating === 0) {
      window.alert("별점은 1점 이상 입력해주세요.");
      return;
    }
    if (!comment) {
      window.alert("댓글을 입력해주세요");
      return;
    }
    const commentData = {
      comment: comment,
      rating: rating,
      userId: 1,
      restaurantId: selectedRestaurant.id,
      restaurantName: selectedRestaurant.place_name,
      id: Math.random()
    };
    setRating(0)
    // POST 요청을 보낼 URL 설정
    const url = "/comments";
    // Axios를 사용하여 POST 요청 보내기
    axios
      .post(url, commentData)
      .then((response) => {
        // 서버로부터 응답이 성공적으로 받아졌을 경우의 처리
        // 예: 댓글이 성공적으로 저장되었다는 알림을 표시하거나, 댓글 목록을 업데이트함
        setComments([...comments, response.data]);
        setComment("")
      })
      .catch((error) => {
        // 서버로부터 응답이 실패한 경우의 처리
        // 예: 에러 메시지를 표시하거나, 에러 상황을 처리함
        console.error("댓글 저장에 실패하였습니다.", error);
      });
  };
  const sendLocationData = (latitude, longitude) => {
    const data = {
      latitude: latitude,
      longitude: longitude,
    };

    axios
      .post("/api/location", data)
      .then((response) => {
        setRestaurantInfo(response.data);
        saveRestaurantInfo(response.data)
      })
      .catch((error) => {
        console.error(error);
      });

  };

  const getLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          sendLocationData(latitude, longitude);
        },
        (error) => {
          console.error(error);
        }
      );
    } else {
      console.error("Geolocation is not supported by this browser.");
    }
  };

  const handleModalShow = (restaurant) => {
    //서버와 통신해서 전체 댓글 가져와야함
    setSelectedRestaurant(restaurant);
    setRating(0); // Reset rating when modal is shown
    setShowModal(true);
  };

  const handleModalClose = () => {
    setShowModal(false);
    setIsEdit(false)
    setComment("")
  };

  const handleStarClick = (star) => {
    setRating(star);
  };
  const handleEditComment = (comment) => {
    setTargetComment(comment);
    setIsEdit(true);
  }
  const handleDeleteComment = (comment) => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      axios
        .delete(`/comments/delete/${comment.commentId}`)
        .then(() => {
          setComments((comments) =>
            comments.filter((c) => c.commentId !== comment.commentId)
          );
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }
  const handleCommentCancel = () => {
    setIsEdit(false);
    setComment("")
    setRating(0)
  }
  return (
    <div>
      <button onClick={getLocation}>위치 찾기</button>
      {restaurantInfo.length > 0 && (
        <table className="restaurant-table">
          <thead>
            <tr>
              <th>장소명</th>
              <th>주소</th>
              <th>전화번호</th>
              <th>카테고리</th>
              <th>거리</th>
            </tr>
          </thead>
          <tbody>
            {restaurantInfo.map((data) => (
              <tr key={data.id}>
                <td onClick={() => handleModalShow(data)}>{data.place_name}</td>
                <td>{data.address_name}</td>
                <td>{data.phone}</td>
                <td>{data.category_name}</td>
                <td>{data.distance}m</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      <Modal show={showModal} onHide={handleModalClose}>
        <Modal.Header closeButton>
          <Modal.Title><h3>{selectedRestaurant && selectedRestaurant.place_name}</h3></Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {/* Modal Body Content */}

          {/* 선택한 식당의 장소명 */}
          <p>Place Name</p>
          <hr />
          {/* 별점 표시 */}
          <ul className="comment-list">
            {comments && selectedRestaurant && comments.filter(
              (comment) =>
                comment.restaurantName === selectedRestaurant.place_name
            ).length > 0 ? (
              !isEdit ? comments.map((comment, idx) => {
                if (comment.restaurantName === selectedRestaurant.place_name) {
                  const fullStars = comment.rating;
                  const emptyStars = 5 - comment.rating;
                  return (
                    <li key={idx} className="comment-item">
                      <div className="comment-avatar">
                        <img src={comment.avatar} alt="Avatar" />
                      </div>
                      <div className="comment-content">
                        <div className="comment-header">
                          <div className="comment-id">
                            ID: {comment.userId}
                          </div>
                          <div className="comment-rating">
                            {Array(fullStars).fill().map((_, i) => (
                              <BsStarFill
                                key={`star-full-${comment.id}-${i}`}
                                style={{ color: "red" }}
                              />
                            ))}
                            {Array(emptyStars).fill().map((_, i) => (
                              <BsStar
                                key={`star-empty-${comment.id}-${i}`}
                                style={{ color: "red" }}
                              />
                            ))}
                          </div>
                          {comment.userId === 1 && (
                            <div className="btn-container">
                              <button className="delete-btn" onClick={() => handleEditComment(comment)}>Edit</button>
                              <button className="edit-btn" onClick={() => handleDeleteComment(comment)}>Delete</button>
                            </div>
                          )}
                        </div>
                        <div className="comment-divider"></div>
                        <div className="comment-text">{comment.comment}</div>
                      </div>
                    </li>
                  );
                } else {
                  return null;
                }
              }) : <li className="comment-item">
                <div className="comment-avatar">
                  <img src={comment.avatar} alt="Avatar" />
                </div>
                <div className="comment-content">
                  <div className="comment-header">
                    <div className="comment-id">
                      ID: {targetComment.userId}
                    </div>
                    <div className="comment-rating">
                      {Array(targetComment.rating).fill().map((_, i) => (
                        <BsStarFill
                          key={`star-full-${comment.id}-${i}`}
                          style={{ color: "red" }}
                        />
                      ))}
                      {Array(5 - targetComment.rating).fill().map((_, i) => (
                        <BsStar
                          key={`star-empty-${comment.id}-${i}`}
                          style={{ color: "red" }}
                        />
                      ))}
                    </div>
                  </div>
                  <div className="comment-divider"></div>
                  <div className="comment-text">{targetComment.comment}</div>
                </div>
              </li>
            ) : (
              <p className="no-comments">No comments yet.</p>
            )}
          </ul>
          <div className="rating-container">
            <p>Rating:</p>
            <div className="star-container">
              {[1, 2, 3, 4, 5].map((star) => (
                <BsStarFill
                  key={star + Math.random()}
                  size={24}
                  color={star <= rating ? "red" : "gray"}
                  onClick={() => handleStarClick(star)}
                  className="star-icon"
                />
              ))}
            </div>
          </div>
          {/* 댓글 입력 폼 */}
          <div className="comment-form-container">
            <p>Comment:</p>
            <textarea
              className="comment-form-textarea"
              value={comment}
              onChange={handleCommentChange}
              placeholder="댓글을 입력하세요."
              rows={4}
            />
            {!isEdit ?
              <Button variant="primary" onClick={handleCommentSave}>
                Save Comment
              </Button> :
              <div>
                <Button style={{ marginRight: "15px" }} variant="primary" onClick={handleCommentUpdate}>
                  Edit Comment
                </Button>
                <Button variant="primary" onClick={handleCommentCancel}>
                  Cancel
                </Button>
              </div>
            }
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleModalClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
export default Container;
