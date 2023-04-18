import React from 'react';

// Comment 컴포넌트 정의
const Comment = ({ id, content }) => {
  return (
    <li>
      ID: {id}, 내용: {content}
    </li>
  );
};

// CommentList 컴포넌트 정의
const Comments = ({ comments }) => {
  return (
    <ul>
      {comments.map(comment => (
        <Comment key={comment.id} id={comment.id} content={comment.content} />
      ))}
    </ul>
  );
};

export default Comments;